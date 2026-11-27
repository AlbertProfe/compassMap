import { AssessmentProvider, useAssessment } from '../context/AssessmentContext.jsx';
import CustomerSelect from '../components/CustomerSelect.jsx';
import Questionnaire from '../components/Questionnaire.jsx';
import ProfileResult from '../components/ProfileResult.jsx';

function AssessmentContent() {
  const { isLoading, error, result, customers } = useAssessment();

  if (isLoading) {
    return <p>Loading customers and questions...</p>;
  }

  return (
    <>
      {error && <p className="error-message">{error}</p>}
      <CustomerSelect />
      {customers.length > 0 && (result ? <ProfileResult /> : <Questionnaire />)}
    </>
  );
}

export default function Assessment() {
  return (
    <section>
      <h2>Assessment</h2>
      <AssessmentProvider>
        <AssessmentContent />
      </AssessmentProvider>
    </section>
  );
}
