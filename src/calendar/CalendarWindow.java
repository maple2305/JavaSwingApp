package calendar;

import java.awt.BorderLayout;
import java.net.URL;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CalendarWindow extends JFrame {

	private LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);

	public CalendarWindow() {
		setTitle("カレンダー");
		setSize(400, 320);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 閉じてもMainWindowは残る
		setLocationRelativeTo(null); //画面中央に表示
		URL IconUrl = getClass().getResource("/images/icon01.png");
		ImageIcon icon = new ImageIcon(IconUrl);
		setIconImage(icon.getImage()); //ウィンドウアイコン設定

		// タイトル
		JLabel title = new JLabel("カレンダー", SwingConstants.CENTER);
		add(title, BorderLayout.NORTH);

		// カレンダー表示用パネル
		CalendarPanel calendarPanel = new CalendarPanel(null);
		add(calendarPanel, BorderLayout.CENTER);

		setVisible(true);
	}
}
