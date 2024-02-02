package org.geekhub.repository;

import org.geekhub.model.Message;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
            INSERT INTO encryption_message (user_id, original_message, encrypted_message, algorithm, date)
            VALUES (:userId, :originalMessage, :encryptedMessage, :algorithm, :date)
            """;
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("userId", message.getUserId())
            .addValue("originalMessage", message.getOriginalMessage())
            .addValue("encryptedMessage", message.getEncryptedMessage())
            .addValue("algorithm", message.getAlgorithm())
            .addValue("date", Timestamp.valueOf(message.getDate()));
        jdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public List<Message> findAll() {
        String sql = "SELECT * FROM encryption_message";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Message(
            rs.getInt("user_id"),
            rs.getString("original_message"),
            rs.getString("encrypted_message"),
            rs.getString("algorithm"),
            formatDate(rs.getDate("date"))
        ));
    }

    private String formatDate(java.sql.Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            return dateFormat.format(date);
        }
        return null;
    }

    @Override
    public List<Message> findByAlgorithm(String algorithm) {
        return null;
    }

    @Override
    public List<Message> findByDate(OffsetDateTime date) {
        return null;
    }

}
