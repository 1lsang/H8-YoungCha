import { PowerTrain } from '@/assets/mock/optionMock';
import { useOptionCardContext } from '@/store/useOptionCardContext';

interface SummarySectionProps extends Pick<PowerTrain, 'details'> {}

function SummarySection({ details }: SummarySectionProps) {
  const { isActive } = useOptionCardContext();
  return (
    <p
      className={`${
        isActive ? 'text-grey-black' : 'text-grey-003'
      } body3 whitespace-pre-wrap`}
    >
      {details[0].description}
    </p>
  );
}

export default SummarySection;
