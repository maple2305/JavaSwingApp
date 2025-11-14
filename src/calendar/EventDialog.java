package calendar;

import java.awt.BorderLayout;
import java.net.URL;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// JFrame じゃなく JDialog を使う
public class EventDialog extends JDialog {

	public EventDialog(JFrame owner, CalendarPanel calendarPanel, String day) {

		super(owner, "イベント登録 - " + day + "日", true); // true=モーダル

		setSize(300, 200);
		setLocationRelativeTo(owner); // カレンダーの中央に表示
		URL IconUrl = getClass().getResource("/images/icon01.png");
		ImageIcon icon = new ImageIcon(IconUrl);
		setIconImage(icon.getImage()); //ウィンドウアイコン設定

		// パネルとレイアウト
		JPanel panel = new JPanel(new BorderLayout(5, 5));

		// ラベル
		JLabel label = new JLabel("イベントを入力：");
		panel.add(label, BorderLayout.NORTH);

		// テキストフィールド
		JTextField textField = new JTextField();
		panel.add(textField, BorderLayout.CENTER);

		// 登録ボタン
		JButton btnSave = new JButton("登録");
		btnSave.addActionListener(e -> {
			String event = textField.getText();
			if (!event.isBlank()) {
				// 日付をLocalDateに変換
				LocalDate date = LocalDate.of(
						calendarPanel.getCurrentMonth().getYear(),
						calendarPanel.getCurrentMonth().getMonthValue(),
						Integer.parseInt(day));

				// CalendarPanel のイベントマップに追加
				calendarPanel.addEvent(date, event);
				dispose(); // ダイアログを閉じる

			} else {
				JOptionPane.showMessageDialog(this, "内容を入力してください");
			}
		});
		panel.add(btnSave, BorderLayout.SOUTH);

		add(panel);
		setVisible(true);
	}
}
