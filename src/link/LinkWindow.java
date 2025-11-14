package link;

import java.awt.BorderLayout;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LinkWindow extends JFrame{
	
	public LinkWindow() {
		setTitle("リンク管理");
		setSize(400,320);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 閉じてもMainWindowは残る
		setLocationRelativeTo(null); //画面中央に表示
		URL IconUrl = getClass().getResource("/images/icon01.png");
		ImageIcon icon = new ImageIcon(IconUrl);
		setIconImage(icon.getImage()); //ウィンドウアイコン設定
		
		// タイトル
		JLabel title = new JLabel("リンク管理", SwingConstants.CENTER);
		add(title, BorderLayout.NORTH);

		// リンク機能表示用パネル
		LinkPanel linkPanel = new LinkPanel();
		add(linkPanel, BorderLayout.CENTER);

		setVisible(true);
	}

}
