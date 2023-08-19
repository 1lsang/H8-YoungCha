import { Link, LinkProps } from 'react-router-dom';
import SelectedBar from './SelectedBar';

interface ProgressBarProps {
  step: number;
  path: 'self' | 'guide';
  id: string;
}

interface ProgressItemProps extends LinkProps {
  isSelected: boolean;
}

const PROGRESS_LIST = [
  '파워트레인',
  '구동 방식',
  '바디 타입',
  '외장 색상',
  '내장 색상',
  '휠 선택',
  '옵션 선택',
  '견적 내기',
];

function ProgressBar({ step, path, id }: ProgressBarProps) {
  return (
    <nav className="relative z-10 text-center min-w-1024px h-26px title5">
      <span className="relative mx-auto whitespace-nowrap">
        <ProgressList {...{ step, path, id }} />
        <SelectedBar active={step - 1} />
      </span>
      <div className="w-full h-0.5 absolute top-22px bg-grey-002" />
    </nav>
  );
}

function ProgressList({ step, path, id }: ProgressBarProps) {
  return PROGRESS_LIST.map((item: string, index: number) => (
    <ProgressItem
      isSelected={index + 1 === step}
      to={`/model/${id}/making/${path}/${index + 1}`}
      key={`ProgressItem-${index}`}
    >
      {`${(index + 1).toString().padStart(2, '0')} ${item}`}
    </ProgressItem>
  ));
}

function ProgressItem({
  children,
  to,
  isSelected,
  ...props
}: ProgressItemProps) {
  return (
    <Link
      to={to}
      className={`w-120px inline-block ${
        isSelected ? 'text-main-blue' : 'text-grey-002'
      } ${isSelected ? 'font-medium' : 'font-normal'}`}
      {...props}
    >
      {children}
    </Link>
  );
}

export default ProgressBar;
