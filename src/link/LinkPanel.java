package link;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class LinkPanel extends JPanel {

	// JTableに表示するためのモデル(データの箱)
	private DefaultTableModel tableModel;

	// 実際に画面に出すテーブル
	private JTable table;

	// データを保存するリスト(一旦DBなし)
	private ArrayList<Link> linkList = new ArrayList<Link>();

	// ===============================
	// コンストラクタ（画面を作る部分）
	// ===============================
	public LinkPanel() {

		// 全体のレイアウトを「上下左右の4つ＋真ん中」にする
		setLayout(new BorderLayout());

		// -------------------------------
		// ■ 上部ボタンエリア
		// -------------------------------
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		// 新しいリンクを追加
		JButton btnAdd = new JButton("追加");
		// 選択したリンクを編集
		JButton btnEdit = new JButton("編集");
		// 選択したリンクを削除
		JButton btnDelete = new JButton("削除");

		// ボタンを上パネルにまとめて設置
		buttonPanel.add(btnAdd);
		buttonPanel.add(btnEdit);
		buttonPanel.add(btnDelete);

		// 上部に配置
		add(buttonPanel, BorderLayout.NORTH);

		// -------------------------------
		// ■ テーブル（一覧表示）
		// -------------------------------
		// テーブルの列名
		String[] columns = { "No", "タイトル", "URL", "カテゴリ", "メモ" };

		// データを入れるモデルを作成
		tableModel = new DefaultTableModel(columns, 0) {
			// No列を触られたくないので全セル編集不可にする
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// モデルを使ってJTableを作成
		table = new JTable(tableModel);

		// テーブルをスクロールできるようスクロールベインに入れる
		add(new JScrollPane(table), BorderLayout.CENTER);

		// -------------------------------
		// ■ ボタンイベント（処理部分）
		// -------------------------------
		btnAdd.addActionListener(e -> addLink());
		btnEdit.addActionListener(e -> editLink());
		btnDelete.addActionListener(e -> deleteLink());

		// -------------------------------
		// ■ URLクリックでブラウザを開くリスナー
		// -------------------------------
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = table.getSelectedRow();
					int col = table.getSelectedColumn();

					if (col == 2) {
						String url = (String) table.getValueAt(row, col);
						try {
							Desktop.getDesktop().browse(new URI(url));
						} catch (Exception ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(LinkPanel.this, "URLを開けません");
						}
					}
				}
			}

		});

	}

	// ===============================
	// ■ リンク追加
	// ===============================
	private void addLink() {

		// 入力用ダイアログを出す(null -> 新規追加モード)
		Link input = showInputDialog(null);

		// OKを押した場合だけ追加
		if (input != null) {
			linkList.add(input); // リストに追加
			refreshTable(); // テーブルを再描画
		}
	}

	// ===============================
	// ■ リンク編集
	// ===============================
	private void editLink() {
		// どの行を選んだか取得
		int row = table.getSelectedRow();

		// 該当のLinkデータを取得
		Link selected = linkList.get(row);

		// 入力ダイアログに"既存の値をセット"して開く
		Link edited = showInputDialog(selected);

		// OKが押された場合のみ更新
		if (edited != null) {
			linkList.set(row, edited);
			refreshTable();
		}
	}

	// ===============================
	// ■ リンク削除
	// ===============================

	// 元のアイコンを読み込む
	ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/awaterupengin.png"));

	// 画像サイズを縮小
	Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

	// 再び ImageIcon に変換
	ImageIcon icon = new ImageIcon(scaledImage);

	private void deleteLink() {
		int row = table.getSelectedRow();

		//本当に削除するか確認
		int confirm = JOptionPane.showConfirmDialog(this,
				"本当に削除しますか？",
				"確認",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				icon);

		if (confirm == JOptionPane.YES_OPTION) {
			linkList.remove(row); // リストから削除
			refreshTable();
		}
	}

	// ===============================
	// ■ 入力ダイアログ（追加・編集共通）
	// ===============================
	private Link showInputDialog(Link defaultValue) {

		// defaultValueがnull -> 新規追加
		// defaultValueが存在 -> 編集モード

		JTextField tfTitle = new JTextField(defaultValue != null ? defaultValue.getTitle() : "");
		JTextField tfUrl = new JTextField(defaultValue != null ? defaultValue.getUrl() : "");
		JTextField tfCategory = new JTextField(defaultValue != null ? defaultValue.getCategory() : "");
		JTextField tfMemo = new JTextField(defaultValue != null ? defaultValue.getMemo() : "");

		// 入力フォームとしてダイアログを構成
		Object[] message = {
				"タイトル：", tfTitle,
				"URL：", tfUrl,
				"カテゴリ：", tfCategory,
				"メモ：", tfMemo,
		};

		int option = JOptionPane.showConfirmDialog(
				this,
				message,
				defaultValue == null ? "リンク追加" : "リンク編集",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		// OK押した場合だけ新しいLinkを返す
		if (option == JOptionPane.OK_OPTION) {
			return new Link(
					tfTitle.getText(),
					tfUrl.getText(),
					tfCategory.getText(),
					tfMemo.getText());
		}
		// キャンセルならnull
		return null;

	}

	// ===============================
	// ■ テーブルを最新状態に更新
	// ===============================
	private void refreshTable() {
		// テーブルの中身を全部クリア
		tableModel.setRowCount(0);

		// linklistの内容を一件ずつテーブルに追加
		for (int i = 0; i < linkList.size(); i++) {
			Link l = linkList.get(i);

			tableModel.addRow(new Object[] {
					i + 1, // No
					l.getTitle(), // タイトル
					l.getUrl(), // URL
					l.getCategory(), // カテゴリ
					l.getMemo() // メモ
			});

		}

	}
}
