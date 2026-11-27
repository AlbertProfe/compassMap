import { useAssessment } from '../context/AssessmentContext.jsx';

export default function Questionnaire() {
  const { questions, answers, answerQuestion, submit, canSubmit, isSubmitting } = useAssessment();

  if (questions.length === 0) {
    return <p>No questions in the database.</p>;
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    submit();
  };

  return (
    <form className="questionnaire" onSubmit={handleSubmit}>
      <ol className="question-list">
        {questions.map((question) => (
          <li className="assessment-card" key={question.id}>
            <p className="question-text">{question.text}</p>
            <label>
              <input
                type="radio"
                name={question.id}
                checked={answers[question.id] === true}
                onChange={() => answerQuestion(question.id, true)}
              />{' '}
              Yes
            </label>
            <label>
              <input
                type="radio"
                name={question.id}
                checked={answers[question.id] === false}
                onChange={() => answerQuestion(question.id, false)}
              />{' '}
              No
            </label>
          </li>
        ))}
      </ol>
      <button type="submit" className="view-button active" disabled={!canSubmit}>
        {isSubmitting ? 'Sending…' : 'Get my profile'}
      </button>
    </form>
  );
}
