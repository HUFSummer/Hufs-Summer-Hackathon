package com.hufs.ice_back.service.implement;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hufs.ice_back.dto.request.PostArticleRequestDto;
import com.hufs.ice_back.dto.response.GetArticleListResponseDto;
import com.hufs.ice_back.dto.response.GetArticleResponseDto;
import com.hufs.ice_back.dto.response.PostArticleResponseDto;
import com.hufs.ice_back.dto.response.ResponseDto;
import com.hufs.ice_back.entity.ArticleEntity;
import com.hufs.ice_back.entity.PositionEntity;
import com.hufs.ice_back.repository.ArticleRepository;
import com.hufs.ice_back.repository.PositionRepository;
import com.hufs.ice_back.repository.UserRepository;
import com.hufs.ice_back.service.ArticleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleServiceImplement implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final PositionRepository positionRepository;
    

    @Override
    public ResponseEntity<? super PostArticleResponseDto> postArticle(PostArticleRequestDto dto, String email){
        try{
            // 사용자 계정이 존재하는지 확인하는 코드
            boolean existedEmail = userRepository.existsByEmail(email);
            if (!existedEmail) return PostArticleResponseDto.notExistUser();
            ArticleEntity articleEntity = new ArticleEntity(dto, email);
            articleRepository.save(articleEntity);
            PositionEntity postitionEntity = new PositionEntity(dto, articleEntity.getArticleNum());
            positionRepository.save(postitionEntity);

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostArticleResponseDto.success();
    }

    @Override
        public ResponseEntity<? super GetArticleResponseDto> getArticle(Integer articleNum) {
            ArticleEntity articleEntity = null;
            PositionEntity positionEntity = null;

            try {
                articleEntity = articleRepository.findByArticleNum(articleNum);
                positionEntity = positionRepository.findByArticleNum(articleNum);
                if (articleEntity == null) return GetArticleResponseDto.noExistArticle();
            } catch (Exception exception){
                exception.printStackTrace();
                return ResponseDto.databaseError();
            }
            return GetArticleResponseDto.success(articleEntity, positionEntity);
        }

    @Override
    public ResponseEntity<? super GetArticleListResponseDto> getArticleList() {
        List<ArticleEntity> articleEntities;
        try {
            articleEntities = articleRepository.findAll();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetArticleListResponseDto.success(articleEntities, positionRepository);
    }
}
