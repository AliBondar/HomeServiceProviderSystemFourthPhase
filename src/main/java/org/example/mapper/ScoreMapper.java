package org.example.mapper;

import org.example.dto.ScoreDTO;
import org.example.entity.Score;

import java.security.NoSuchAlgorithmException;

public class ScoreMapper implements BaseMapper<ScoreDTO, Score> {
    @Override
    public Score convert(ScoreDTO scoreDTO) {
        Score score = new Score();
        score.setScore(scoreDTO.getScore());
        score.setComment(scoreDTO.getComment());
        score.setClient(scoreDTO.getClient());
        score.setOrder(scoreDTO.getOrder());
        score.setExpert(scoreDTO.getExpert());
        return score;
    }

    @Override
    public ScoreDTO convert(Score score) {
        ScoreDTO scoreDTO = new ScoreDTO();
        scoreDTO.setScore(score.getScore());
        scoreDTO.setComment(score.getComment());
        scoreDTO.setClient(score.getClient());
        scoreDTO.setExpert(score.getExpert());
        scoreDTO.setOrder(score.getOrder());
        return scoreDTO;
    }
}
