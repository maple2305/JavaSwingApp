package db.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import db.dao.EventDao;
import db.entity.Event;

public class EventTest {
	
	public static void main(String[] args) {
		
		Event event = new Event("刀ステ",
				LocalDateTime.parse("2025-11-01 19:00:00",DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
				"TDC","18:00 祐子と駅待ち合わせ");
		
		EventDao dao = new EventDao();
		dao.create(event);
		
	}

}
