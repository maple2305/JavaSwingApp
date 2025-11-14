package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.swing.JLabel;

/**
 * OshiCounter
 * 「推し始めて〇日」を管理・表示するクラス
 * - 日付をファイルに保存
 * - 保存日から今日までの日数を計算
 * - JLabel に日数を表示
 */

public class Counter {

	private JLabel lbl; // 日数を表示するラベル
	private File saveFile; // 推し開始日を保存するファイル
	private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * コンストラクタ
	 * @param lbl 日数を表示する JLabel
	 */
	public Counter(JLabel lbl) {
		this.lbl = lbl;
		this.saveFile = new File("start.txt"); //保存ファイルを設定
		loadStartDate(); //アプリ起動時に保存日を読みこむ
	}

	/**
     * 推し開始日を登録し、ファイルに保存
     * @param dateStr 日付文字列 (yyyy-MM-dd)
     */
	// try～catchでなければ例外がでたときクラッシュする
	public void setStartDate(String dateStr) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))){
				writer.write(dateStr); //ファイルに日付を書き込む
				updateLabel(dateStr); //ラベルも更新
	}catch(Exception e) {
		e.printStackTrace();
	}}
		
	    /**
	     * 日数ラベルを更新
	     * @param startDateStr 登録された日付文字列
	     */
	public void updateLabel(String startDateStr) {
		try {
			LocalDate startDate = LocalDate.parse(startDateStr,fmt); //登録日をLocalDateに変換
			LocalDate today = LocalDate.now(); //今日の日付
			long days = ChronoUnit.DAYS.between(startDate, today)+1; // 日数計算（開始日も1日目）
			lbl.setText("<html>推し始めて <b><span style='font-size:24px;'>"
			+ days +" </span></b> 日</html>");	// ラベルに反映
		} catch (Exception e) {
			lbl.setText("推し初めて０日"); // 例外は0日
		}
	}
    /**
     * 保存されている推し開始日を読み込み、ラベルを更新
     */
	public void loadStartDate() {
		if(saveFile.exists()) { //ファイルが存在する場合のみ読み込む
			try(BufferedReader reader = new BufferedReader(new FileReader(saveFile))){
				String dateStr = reader.readLine(); // 1行目を読み込む
				if(dateStr != null) updateLabel(dateStr); // ラベルを更新
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}

