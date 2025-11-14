package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;
import java.util.Enumeration;

import javax.swing.ImageIcon; //アイコン画像
import javax.swing.JButton;
import javax.swing.JFrame; //ウィンドウ
import javax.swing.JLabel; //文字表示ラベル
import javax.swing.JPanel;
import javax.swing.UIManager;

//extends JFrame…JFrame（ウィンドウ）を継承
//implements ActionListener…ボタン押下などのイベントを処理するための仕組み を使う
public class MainWindow extends JFrame {

	public static void main(String[] args) {
		MainWindow app = new MainWindow();
		// フレームの表示
		app.setVisible(true); //setVisible(true)…ウィンドウ画面に表示
	}

	// コンストラクター内で画面レイアウトを設定
	public MainWindow() {
		//アプリ全体のフォントを統一
		Font globalFont = new Font("Kaisei Decol", Font.PLAIN, 16);
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof Font) {
				UIManager.put(key, globalFont);
			}
		}

		//ウィンドウのサイズや位置、タイトル、アイコンを設定
		//setLayout(null); //自分で座標を指定して部品を配置する
		setSize(400, 320); //ウィンドウサイズ
		setLocation(100, 200); //画面上の表示位置
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //×で終了
		setTitle("OshiBox"); //タイトル
		//		setLayout(null);
		URL IconUrl = getClass().getResource("/images/icon01.png");
		ImageIcon icon = new ImageIcon(IconUrl);
		setIconImage(icon.getImage()); //ウィンドウアイコン設定
		//getContentPane().setLayout(new FlowLayout());

		//トップ背景
		JPanel panelTop = new JPanel();
		panelTop.setBackground(Color.decode("#fff5e9"));
			// ★panelTop…推しの画像
		URL topUrl = getClass().getResource("/images/top.png");
		ImageIcon topIcon = new ImageIcon(topUrl);
			//サイズ変更
		int width = 300; //幅
		int height = 200; //高さ
		Image scaledImage = topIcon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		JLabel imageLabel = new JLabel(scaledIcon);
		panelTop.add(imageLabel);

		//レフト背景
		//JPanel panelLeft = new JPanel();

		// センター：中央パネル
		JPanel panelCenter = new JPanel(new BorderLayout()); // 画面切り替え用
		add(panelCenter, BorderLayout.CENTER);
		panelCenter.setBackground(Color.decode("#e6e6fa"));
		
		// 小パネルを作ってボタンを配置
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		panelCenter.add(buttonPanel, BorderLayout.CENTER); // ボタンは小パネルに配置
		buttonPanel.setBackground(Color.decode("#e6e6fa"));
		
		//センター①：カレンダー
		JButton btnCalendar = new Button("カレンダー");
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.gridx = 0; // 横の列
		gbc1.gridy = 0; // 縦の列
		gbc1.insets = new Insets(0, 0, 0, 20);
		// クリックしたら CalendarWindow を開く
		// addActionListener：ボタンが押されたときに実行する処理を登録する
		btnCalendar.addActionListener(e -> new calendar.CalendarWindow());
		buttonPanel.add(btnCalendar, gbc1);

		// センター②：リンク集
		JButton btnlink = new Button("リンク集");
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 1; // 横の列
		gbc2.gridy = 0; // 縦の列
		gbc2.insets = new Insets(0, 20, 0, 0);
		btnlink.addActionListener(e -> new link.LinkWindow());
		buttonPanel.add(btnlink, gbc2);

		//ライト背景
		//JPanel panelRight = new JPanel();

		//ボトム背景
		JPanel panelBottom = new JPanel();
		panelBottom.setBackground(Color.decode("#ffe5c0"));

		// ボトムlbl2：カウントアップ
		JLabel lbl2 = new JLabel("推し始めて０日");
		// 日数管理用クラス
		Counter counter = new Counter(lbl2);
		// btn5：登録ボタン
		JButton btn5 = new Button("登録");
		btn5.setPreferredSize(new Dimension(100, 40));
		btn5.addActionListener(e -> {
			String input = javax.swing.JOptionPane.showInputDialog(
					this,
					"推し始めた日は？？(yyyy-MM-dd)");
			if (input != null && !input.isBlank()) {
				counter.setStartDate(input);
			}
		});
		panelBottom.add(lbl2);
		panelBottom.add(btn5);

		add(panelTop, BorderLayout.NORTH);
		//add(panelLeft, BorderLayout.WEST);
		add(panelCenter, BorderLayout.CENTER);
		//add(panelRight, BorderLayout.EAST);
		add(panelBottom, BorderLayout.SOUTH);
	}

}
