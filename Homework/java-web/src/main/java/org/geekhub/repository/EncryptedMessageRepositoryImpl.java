package org.geekhub.repository;

import org.geekhub.exception.EncryptException;
import org.geekhub.model.Message;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    private Message getMessage(ResultSet rs) throws SQLException {
        return new Message(
            rs.getInt("user_id"),
            rs.getString("original_message"),
            rs.getString("encrypted_message"),
            rs.getString("algorithm"),
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
        String sql = "SELECT * FROM encryption_message";
        return jdbcTemplate.query(sql, (rs, rowNum) -> getMessage(rs));
    }

    @Override
    public List<Message> findByAlgorithm(String algorithmEncryption) {
        String sql = "SELECT * FROM encryption_message WHERE algorithm = :algorithm";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
        .addValue("algorithm", algorithmEncryption);

        return jdbcTemplate.query(sql, parameterSource, (rs, rowNum) -> getMessage(rs));
    }

    @Override
    public List<Message> findByDate(OffsetDateTime dateFrom, OffsetDateTime dateTo) {
        String sql = getDateByRange(dateFrom, dateTo);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("dateFrom", dateFrom)
            .addValue("dateTo", dateTo);

        return jdbcTemplate.query(sql, parameterSource, (rs, rowNum) -> getMessage(rs));
    }

    private String getDateByRange(OffsetDateTime dateFrom, OffsetDateTime dateTo) {
        if (dateFrom == null && dateTo == null) {
          throw new EncryptException(
              "Please indicate at least one of the dates: dateFrom = null, dateTo = null"
          );
        } else if (dateFrom == null || dateTo == null) {
            return """
                SELECT * FROM encryption_message
                        WHERE date AT TIME ZONE 'UTC' <= :dateTo
                        OR date AT TIME ZONE 'UTC' >= :dateFrom
                        ORDER BY algorithm
                """;
        }
            return """
        SELECT * FROM encryption_message
        WHERE (:dateFrom IS NULL OR date AT TIME ZONE 'UTC' >= :dateFrom)
          AND (:dateTo IS NULL OR date AT TIME ZONE 'UTC' <= :dateTo)
        ORDER BY algorithm
        """;
    }

}
