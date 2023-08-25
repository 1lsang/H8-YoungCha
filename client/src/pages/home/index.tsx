import { TEXT } from './constant';
import useFetch from '@/hooks/useFetch';

import * as Icon from '../../assets/icons';

import BasicOptionBox from './BasicOptionBox';
import CarsImageBox from './CarsImageBox';
import CarsNameListBox from './CarsNameListBox';
import GuideModeButton from './GuideModeButton';
import MainOptionBox from './MainOptionBox';
import MakingCarButtonsBox from './MakingCarButtonsBox';
import TrimCardsBox from './TrimCardsBox';
import InternalColorBox from './InternalColorBox';
import ExteriorColorBox from './ExteriorColorBox';
import { TrimType } from '@/types';
import { useEffect, useRef } from 'react';
interface TrimsDataType {
  model: string;
  trims: TrimType[];
}
function Home() {
  const { data, loading } = useFetch<TrimsDataType>({
    url: '/cars/1/details',
  });
  const trims = loading ? [] : data?.trims.filter((trim) => trim.id !== 5);

  const secondPageRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    sessionStorage.clear();
  }, []);
  return (
    <div>
      {!loading && (
        <>
          <div className="relative flex justify-center w-full h-screen bg-center bg-cover pt-85px bg-main-background-image">
            <div className="flex flex-col justify-between max-w-5xl py-16px">
              <div>
                <p className="text-white text-24px font-hsans-head tracking-[-0.96px] leading-[31.2px]">
                  {TEXT.MAKING_MY_CAR}
                </p>
                <p className="font-medium text-white text-64px font-hsans-head leading-[83.2px]">
                  {data?.model}
                </p>
              </div>

              <div>
                <TrimCardsBox trims={trims} />
                <div className="flex flex-col items-center">
                  <p className="text-white opacity-60 title mt-24px">
                    {TEXT.MAIN_DETAIL_COMPARE}
                  </p>

                  <button
                    className="relative flex flex-col items-center top-12px animate-bounce"
                    onClick={() =>
                      secondPageRef.current?.scrollIntoView({
                        behavior: 'smooth',
                      })
                    }
                  >
                    <Icon.MainBelowArrow opacity={0.8} />
                    <Icon.MainBelowArrow
                      opacity={0.4}
                      className="relative bottom-18px"
                    />
                  </button>
                </div>
              </div>
            </div>
            <div
              className="absolute bottom-0 w-full h-80px z-[-1]"
              ref={secondPageRef}
            ></div>
          </div>
          <div className="w-full pb-400px">
            <CarsNameListBox trims={trims} />
            <div className="flex flex-col items-center pt-32px gap-60px ">
              <CarsImageBox trims={trims} />
              <div className="flex flex-col max-w-5xl gap-48px">
                <MainOptionBox trims={trims} />
                <ExteriorColorBox trims={trims} />
                <InternalColorBox trims={trims} />
                <BasicOptionBox trims={trims} />
                <MakingCarButtonsBox />
                <GuideModeButton />
              </div>
            </div>
          </div>
        </>
      )}
    </div>
  );
}

export default Home;
