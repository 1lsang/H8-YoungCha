import { useCallback, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import DictionaryToggleButton from './DictionaryToggleButton';
import ModelChangeButton from './ModelChangeButton';
import * as Icon from '../../assets/icons';

const TEXT_MAKING_MY_CAR = '내 차 만들기';

function Header() {
  const [backgroundColor, setBackgroundColor] = useState('');

  const handleScroll = useCallback(() => {
    if (window.scrollY === 0) setBackgroundColor('');
    else if (window.scrollY > 0) setBackgroundColor('bg-grey-001');
  }, [setBackgroundColor]);

  useEffect(() => {
    document.addEventListener('scroll', handleScroll);
    return () => {
      document.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return (
    <header
      className={`${backgroundColor} z-20 transition-colors duration-300 ease-out fixed top-0 w-full h-80px select-none min-w-768px px-32px`}
    >
      <div className="flex items-center justify-between h-full max-w-5xl mx-auto xl:max-w-none xl:px-96px">
        <div className="flex h-24px gap-20px">
          <Link to="/">
            <Icon.HDLogo className="w-45px lg:w-166px h-23px" />
          </Link>
          <span>|</span>
          <span className="font-medium title3">{TEXT_MAKING_MY_CAR}</span>
        </div>
        <div className="flex gap-20px">
          <DictionaryToggleButton />
          <span>|</span>
          <ModelChangeButton />
        </div>
      </div>
    </header>
  );
}

export default Header;
