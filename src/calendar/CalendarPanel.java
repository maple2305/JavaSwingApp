package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

// カレンダーのパネルを作るクラス
// MainWindowやCalendarWindowから呼び出して表示できる
public class CalendarPanel extends JPanel {

	private YearMonth currentMonth; // 現在表示している年月を保持
	private JPanel daysPanel; // 日付ボタンを配置するパネル
	private JLabel lblMonth; // 上部の当月表示のパネル
	private JFrame owner; // EventDialog表示用の親ウィンドウ

	// イベント登録ダイアログから呼び出すときに登録する
	private Map<LocalDate, String> events = new HashMap<>();

	// コンストラクタ
	/*
	 ownerはEnentDialogを開く際に親ウィンドウとして渡すため
	(ownerがないとダイアログがウィンドウの背後に隠れたりする)
	*/
	public CalendarPanel(JFrame owner) {
		this.owner = owner;
		this.currentMonth = YearMonth.now(); // 最初は今月を表示

		setLayout(new BorderLayout()); // 上：ヘッダー/中：カレンダー

		// ①上部：タイトルバー（例：← 2025年10月 →）
		JPanel headerPanel = new JPanel(new BorderLayout());

		JButton btnPrev = new JButton("←");
		JButton btnNext = new JButton("→");
		lblMonth = new JLabel("", SwingConstants.CENTER);
		lblMonth.setFont(lblMonth.getFont().deriveFont(15)); // 少し大きめに

		headerPanel.add(btnPrev, BorderLayout.WEST);
		headerPanel.add(lblMonth, BorderLayout.CENTER);
		headerPanel.add(btnNext, BorderLayout.EAST);
		add(headerPanel, BorderLayout.NORTH);

		// ボタンに機能を設定
		btnPrev.addActionListener(e -> changeMonth(-1)); // 前月へ
		btnPrev.setBackground(Color.decode("#f5f5f5"));
		
		btnPrev.setOpaque(true); // 色を反映させる
		btnNext.addActionListener(e -> changeMonth(1)); // 翌月へ
		btnNext.setBackground(Color.decode("#f5f5f5"));
		btnNext.setOpaque(true); // 色を反映させる

		// 中央：日付ボタン用パネル（行数は自動、列は7列）
		daysPanel = new JPanel(new GridLayout(0, 7));
		add(daysPanel, BorderLayout.CENTER); // 日付パネルをセンターに配置

		// カレンダーを初期表示
		updateCalender();

	}

	// カレンダーを更新表示するメソッド
	private void updateCalender() {
		// 表示している年月をラベルにセット
		lblMonth.setText(currentMonth.getYear() + "年" +
				currentMonth.getMonthValue() + "月");

		// 前の表示をクリア
		daysPanel.removeAll();

		// 曜日ラベル（1行7列）
		String[] weekdays = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
		for (String dayName : weekdays) {
			JLabel lbl = new JLabel(dayName, SwingConstants.CENTER); // 中央揃え
			// 日曜は赤、土曜は青、平日は黒
			lbl.setForeground(dayName.equals("Sun") ? Color.decode("#fa8072")
					: dayName.equals("Sat") ? Color.decode("#1e90ff") : Color.BLACK);
			daysPanel.add(lbl); // パネルに追加			
		}

		// その月の一日を取得
		/*
		 atDay(int dayOfMonth)メソッドは指定した日（1～月末）を、
		その月の日付として LocalDate に変換する
		LocalDate には 年・月・日・曜日 の情報が含まれるので、カレンダー表示に便
		*/
		LocalDate firstOfMonth = currentMonth.atDay(1);
		// 1日の曜日を取得(日曜スタートの値に変換)
		/*
		 * DayOfWeek には getValue() というメソッドがある
		 * （月曜 = 1、火曜 = 2 … 日曜 = 7 という整数値を返す）
		 * カレンダーを 日曜始まり にしたい場合：
		 * Javaの DayOfWeek は月曜 = 1 なので、日曜 = 7
		 * 日曜を 0 にしたいので 7で割った余り を使う
		 */
		int firstDayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;
		// 今月の日数を取得
		int daysInMonth = currentMonth.lengthOfMonth();

		// 曜日合わせのため1日までの空白を作る(前月末分）
		for (int i = 0; i < firstDayOfWeek; i++) {
			daysPanel.add(new JLabel("")); // 空白ラベル
		}
		// 日付ボタンを作るループ
		for (int day = 1; day <= daysInMonth; day++) {
			final int selectDay = day;
			// 日付文字のボタン
			JButton dayButton = new JButton(String.valueOf(day)) {

				@Override
				protected void paintComponent(Graphics g) {
					g.setColor(getBackground());
					g.fillOval(0, 0, getWidth(), getHeight()); // 丸に描画
					super.paintComponent(g);
				}
			};
			dayButton.setContentAreaFilled(false); // デフォルトの背景を消す

			// 今日の日付を取得
			LocalDate today = LocalDate.now();

			// 今日の日付のカラー変更
			if (currentMonth.getYear() == today.getYear() &&
					currentMonth.getMonth() == today.getMonth() &&
					day == today.getDayOfMonth()) {
				dayButton.setBackground(Color.decode("#f0e68c"));
			}

			// イベントが登録されていたら色変え
			LocalDate date = currentMonth.atDay(day);
			if (events.containsKey(date)) {
				dayButton.setBackground(Color.decode("#d8b4f2")); // ラベンダー色
				dayButton.setToolTipText(events.get(date)); // マウスホバーでイベント内容
			}

			// ボタン押下時にイベント登録ダイアログを開く
			dayButton.addActionListener(e -> new EventDialog(owner, this, String.valueOf(selectDay)));
			daysPanel.add(dayButton); // パネルに追加
		}

		// 変更を反映して再描画
		daysPanel.revalidate();
		daysPanel.repaint();

	}

	// ◀▶ボタンで月を切り替える処理
	private void changeMonth(int diff) {
		currentMonth = currentMonth.plusMonths(diff);
		updateCalender();

	}

	public LocalDate getCurrentMonth() {
		return currentMonth.atDay(1); // 1日の日付を返す
	}

	public void addEvent(LocalDate date, String event) {
		events.put(date, event);
		updateCalender(); // カレンダー再描画

	};

};
