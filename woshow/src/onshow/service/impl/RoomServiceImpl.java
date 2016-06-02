package onshow.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import onshow.domain.Room;
import onshow.service.RoomService;

public class RoomServiceImpl implements RoomService {

	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Room> getRoomList() {
		String sql = "select * from os_room";
		return jdbcTemplate.query(sql, new RoomMapper());
	}
	
	private static final class RoomMapper implements RowMapper<Room>{

		@Override
		public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
			Room room = new Room();
			room.setRoomId(rs.getInt("id"));
			room.setRoomName(rs.getString("room_name"));
			room.setDescription(rs.getString("description"));
			room.setPraiseCount(rs.getInt("praise_count"));
			room.setExpenditure(rs.getInt("expenditure"));
			return room;
		}
		
	}
	
}
