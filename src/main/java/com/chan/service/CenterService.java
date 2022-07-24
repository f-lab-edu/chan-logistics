package com.chan.service;

import com.chan.domain.Center;
import com.chan.exception.CenterFindFailedException;
import com.chan.repository.CenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CenterService {

    private final CenterRepository centerRepository;

    @Transactional
    public Center addCenter(Center center){

        checkCenter(center);

        Center result = centerRepository.save(center);

        return result;
    }

    public Center findCenter(String localCode){

        Center center = centerRepository.findByLocalCode(localCode);

        if(center == null){
            throw new CenterFindFailedException("센터가 존재하지 않습니다.");
        }

        return center;

    }

    private void checkCenter(Center center){
        Center findCenter = centerRepository.findByLocalCode(center.getLocalCode());

        if(findCenter != null){
            throw new CenterFindFailedException("같은 지역코드를 가진 Center가 존재합니다.");
        }

    }
}
