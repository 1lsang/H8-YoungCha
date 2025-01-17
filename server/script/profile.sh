#! /bin/bash

# nginx와 연결되지 않은 profile 찾기(활성화시킬 profile)
function find_idle_profile() {
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" https://api.youngcha.team/profiles)

  if [ "$RESPONSE_CODE" -ge 400 ]
  then
    # nginx와 연결된 컨테이너가 없으면 spring2가 연결되어 있다고 설정
    CURRENT_PROFILE=spring2
  else
    CURRENT_PROFILE=$(curl -s https://api.youngcha.team/profiles)
  fi

  # 구동할 profile 설정
  if [ "$CURRENT_PROFILE" == spring1 ]
  then
    IDLE_PROFILE=spring2
  else
    IDLE_PROFILE=spring1
  fi

  echo "$IDLE_PROFILE"
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port(){
  IDLE_PROFILE=$(find_idle_profile)

  if [ "$IDLE_PROFILE" == spring1 ]
  then
    echo "8081"
  else
    echo "8082"
  fi
}