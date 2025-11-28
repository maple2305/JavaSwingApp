package db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import common.DB_conn;

public class EventDao {
	// CREATE（新規登録）
	public boolean create(Event event) {
		boolean result = false;

		try (Connection objCon = DriverManager.getConnection(
				"jdbc:sqlserver:" + DB_conn.dbHost + ";encrypt=false;"
						+ "databaseName=" + DB_conn.dbName + ";",
				DB_conn.user, DB_conn.pass);) {

			String sql = """
						 INSERT INTO Event (event_name,event_datetime,event_place,event_memo)
						 VALUES (?,?,?,?);
					""";
			PreparedStatement ps = objCon.prepareStatement(sql);
			ps.setString(1, event.getEventName());
			ps.setTimestamp(2, java.sql.Timestamp.valueOf(event.getEventDatetime()));
			ps.setString(3, event.getEventPlace());
			ps.setString(4, event.getEventMemo());

			int resultCnt = ps.executeUpdate();
			if (resultCnt == 1) {
				result = true;
			}
		} catch (Exception e) {
			//コンソールに「接続エラー内容」を表示
			e.printStackTrace();
		}

		return result;
	}

	// READ(シングル/マルチ)
	public List<Event> findAll() {
		// Eventオブジェクトを入れるためのリストを作成(SELECTの結果を入れる箱)
		List<Event> eventList = new ArrayList<>();

		// try-with-resources構文を使ってDB接続（処理終了時に自動でクローズされる）
		try (Connection objCon = DriverManager.getConnection(
				"jdbc:sqlserver:" + DB_conn.dbHost + ";encrypt=false;"
						+ "databaseName=" + DB_conn.dbName + ";",
				DB_conn.user, DB_conn.pass);) {

			// 実行したいSQL文を用意(Eventテーブルの全データを取得)
			String sql = """
					SELECT event_id,event_name,event_datetime,event_place,event_memo
					FROM Event;
					""";

			// SQL文を実行するためのPreparedStatementを作成
			PreparedStatement ps = objCon.prepareStatement(sql);

			// SQLを実行して結果を取得(SELECTの結果がrsに入る)
			ResultSet rs = ps.executeQuery();

			// 結果セット(rs)に1行ずつデータがある間はループする
			while (rs.next()) {
				// Eventクラスのインスタンスを新しく作る
				Event event = new Event();

				// rsからカラムの値を取り出してEventのフィールドにセット
				event.setEventId(rs.getInt("event_id"));
				event.setEventName(rs.getString("event_name"));
				// SQLの日時型はTimestamp型なので、LocalDateTimeに変換してセット
				event.setEventDatetime(rs.getTimestamp("event_datetime").toLocalDateTime());
				event.setEventPlace(rs.getString("event_place"));
				event.setEventMemo(rs.getString("event_memo"));

				// 作成したEventオブジェクトをリストに追加
				eventList.add(event);
			}
		} catch (Exception e) {
			// 例外（エラー）が発生した場合は内容を出力して確認
			e.printStackTrace();
		}

		// 最終的に、取得したEventのリストを返す
		return eventList;

	}
	
	public Event findById(int eventId) {
		// 戻り値用のEventオブジェクトを用意(まだ中身はnull)
		Event event = null ;
		
		// try-with-resources構文を使ってDB接続（処理終了時に自動でクローズされる） 
		try (Connection objCon = DriverManager.getConnection(
				"jdbc:sqlserver:" + DB_conn.dbHost + ";encrypt=false;"
						+ "databaseName=" + DB_conn.dbName + ";",
				DB_conn.user, DB_conn.pass);) {
			
			// SQL文を準備(？ の部分に指定した event_id の値を入れる)
			String sql = """
					SELECT event_id,event_name,event_datetime,event_place,event_memo
					FROM Event
					WHERE event_id = ?;
					""";

			// SQL文を実行するためのPreparedStatementを作成
			PreparedStatement ps = objCon.prepareStatement(sql);
			
			// SQL内の「？」にメソッド引数で受け取った eventId をセット
			ps.setInt(1, eventId);

			// SQLを実行して結果を取得(SELECTの結果がrsに入る)
			ResultSet rs = ps.executeQuery();
			
			// 結果が1行だけ存在する場合(=該当するIDのイベントがある場合)
			if(rs.next()) {
				// 新しいEventオブジェクトを作成
				event = new Event();
				
				// 取得した各カラムの値をEventオブジェクトに格納
				event.setEventId(rs.getInt("event_id"));
				event.setEventName(rs.getString("event_name"));
				event.setEventDatetime(rs.getTimestamp("event_datetime").toLocalDateTime());
				event.setEventPlace(rs.getString("event_place"));
				event.setEventMemo(rs.getString("event_memo"));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return event;
		// 見つからなければnullのまま返る
	}
}
// UPDATE
// DELETE
