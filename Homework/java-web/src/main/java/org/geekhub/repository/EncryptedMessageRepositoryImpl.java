package org.geekhub.repository;

import org.geekhub.model.Message;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class EncryptedMessageRepositoryImpl implements EncryptedMessageRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EncryptedMessageRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveMessage(Message message) {
        String sql = """
            INSERT INTO history (
            user_id,
            original_message,
            encrypted_message,
            algorithm,
            operation,
            date)
            VALUES (
            :userId,
            :originalMessage,
            :encryptedMessage,
            :algorithm,
            :operation,
            :date)
            """;
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("userId", message.getUserId())
            .addValue("originalMessage", message.getOriginalMessage())
            .addValue("encryptedMessage", message.getEncryptedMessage())
            .addValue("algorithm", message.getAlgorithm())
            .addValue("operation", message.getOperation())
            .addValue("date", Timestamp.valueOf(message.getDate()));

        jdbcTemplate.update(sql, parameterSource);
    }

    private Message getMessage(ResultSet rs) throws SQLException {
        return new Message(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getString("original_message"),
            rs.getString("encrypted_message"),
            rs.getString("algorithm"),
            rs.getString("operation"),
            formatDate(rs.getTimestamp("date"))
        );
    }

    private String formatDate(Timestamp timestamp) {
        if (timestamp != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            return dateFormat.format(timestamp);
        }
        return null;
    }

    @Override
    public List<Message> findAll() {
        String sql = "SELECT * FROM history ORDER BY id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> getMessage(rs));
    }

    @Override
    public List<Message> findByAlgorithm(String algorithmEncryption) {
        String sql = "SELECT * FROM history WHERE algorithm = :algorithm ORDER BY id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("algorithm", algorithmEncryption);

        return jdbcTemplate.query(sql, parameterSource, (rs, rowNum) -> getMessage(rs));
    }

    @Override
    public List<Message> findByDate(OffsetDateTime dateFrom, OffsetDateTime dateTo) {
        String sql =
            """
                SELECT * FROM history
                        WHERE date AT TIME ZONE 'UTC' BETWEEN :dateFrom AND :dateTo
                        ORDER BY id
                """;
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("dateFrom", dateFrom)
            .addValue("dateTo", dateTo);

        return jdbcTemplate.query(sql, parameterSource, (rs, rowNum) -> getMessage(rs));
    }

    @Override
    public List<Message> getPaginateHistory(int pageNum, int pageSize) {
        String sql = "SELECT * FROM history ORDER BY date LIMIT :pageSize OFFSET :offset";

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("pageSize", pageSize)
            .addValue("offset", getOffset(pageNum, pageSize));

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> getMessage(rs));
    }

    private static int getOffset(int pageNum, int pageSize) {
        return pageNum * pageSize - 1;
    }
}
