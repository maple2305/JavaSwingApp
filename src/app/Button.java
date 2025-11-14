package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

public class Button extends JButton {

	public Button(String text) {
		super(text);
		setFocusPainted(false); // 枠のフォーカス線を消す
		setBorderPainted(false); // ボーダーを消す
		setRolloverEnabled(true); // ホバー検知ON
		setContentAreaFilled(false); // 背景を自前で描く
		setForeground(new Color(80, 50, 30)); // 文字色（少しブラウン系）
		setFont(new Font("Kaisei Decol", Font.BOLD, 30)); // かわいいフォント
		setPreferredSize(new Dimension(200, 200));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		// アンチエイリアス（ギザギザした線（ジャギー）を滑らかに見せる）でなめらかに
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// 背景色
		if (getModel().isArmed()) {
			g2.setColor(Color.decode("#d8bfd8")); // 押したとき変わる
		} else if (getModel().isRollover()) {
			g2.setColor(new Color(0xffc0cb)); // ホバー時
		} else {
			g2.setColor(new Color(0xffdab9)); // 通常時の背景
		}

		// 丸いボタン
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

		// 枠線
		g2.setColor(new Color(0x9370db));
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

		g2.dispose();
		super.paintComponent(g);
	}
}
