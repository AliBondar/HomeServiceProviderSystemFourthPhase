package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.ScoreDTO;
import org.example.entity.Score;
import org.example.mapper.ScoreMapper;
import org.example.repository.ScoreRepository;
import org.example.service.ScoreService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;

    private final ScoreMapper scoreMapper = new ScoreMapper();

    @Override
    public void save(ScoreDTO scoreDTO){
        Score score = scoreMapper.convert(scoreDTO);
        scoreRepository.save(score);
    }

    @Override
    public void delete(ScoreDTO scoreDTO){
        Score score = scoreMapper.convert(scoreDTO);
        scoreRepository.delete(score);
    }

    @Override
    public ScoreDTO findById(Long id){
        Optional<Score> score = scoreRepository.findById(id);
        return score.map(scoreMapper::convert).orElse(null);
    }

    @Override
    public List<ScoreDTO> findAll(){
        List<Score> scores = scoreRepository.findAll();
        List<ScoreDTO> scoreDTOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(scores)) return null;
        else {
            for (Score score : scores){
                scoreDTOList.add(scoreMapper.convert(score));
            }
        }
        return scoreDTOList;
    }
}
