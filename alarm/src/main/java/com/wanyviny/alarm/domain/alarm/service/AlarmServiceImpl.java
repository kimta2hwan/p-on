package com.wanyviny.alarm.domain.alarm.service;

import com.wanyviny.alarm.domain.alarm.dto.AlarmDto;
import com.wanyviny.alarm.domain.alarm.entity.Alarm;
import com.wanyviny.alarm.domain.alarm.repository.AlarmRepository;
import com.wanyviny.alarm.domain.user.entity.User;
import com.wanyviny.alarm.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService{

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    @Override
    public List<AlarmDto.getAlarmDto> getAlarm(Long userId) {
        List<Alarm> alarmList = alarmRepository.findByUserId(userId);

        return alarmList.stream()
                .map(Alarm::entityToDto)
                .sorted()
                .toList();
    }

    @Override
    public void postAlarm(User user, AlarmDto.setAlarmDto alarmDto) {
        alarmRepository.save(alarmDto.dtoToEntity(user));
    }

    @Override
    public void deleteAlarm(Long userId, Long alarmId) {
        userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저가 없습니다.")
        );

        alarmRepository.deleteByAlarmId(alarmId);
    }
}
