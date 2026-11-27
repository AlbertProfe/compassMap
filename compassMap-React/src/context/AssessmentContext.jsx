import { createContext, useContext, useEffect, useState } from 'react';
import { getCustomers, getQuestions, submitAssessment } from '../api/compassMapApi.js';

const AssessmentContext = createContext(null);

export function AssessmentProvider({ children }) {
  const [customers, setCustomers] = useState([]);
  const [questions, setQuestions] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [selectedCustomerId, setSelectedCustomerId] = useState('');
  const [answers, setAnswers] = useState({});
  const [result, setResult] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    Promise.all([getCustomers(), getQuestions()])
      .then(([customersData, questionsData]) => {
        setCustomers(customersData);
        setQuestions([...questionsData].sort((a, b) => a.order - b.order));
      })
      .catch(() => setError('Could not load customers and questions. Is the backend running?'))
      .finally(() => setIsLoading(false));
  }, []);

  const selectCustomer = (id) => setSelectedCustomerId(id);

  const answerQuestion = (questionId, value) =>
    setAnswers((prev) => ({ ...prev, [questionId]: value }));

  const submit = () => {
    setIsSubmitting(true);
    setError(null);
    submitAssessment(selectedCustomerId, answers)
      .then((data) => setResult(data))
      .catch((err) => setError(err.response?.data?.message || 'Could not submit the assessment.'))
      .finally(() => setIsSubmitting(false));
  };

  const reset = () => {
    setAnswers({});
    setResult(null);
    setError(null);
  };

  const canSubmit =
    selectedCustomerId !== '' &&
    questions.length > 0 &&
    questions.every((q) => q.id in answers) &&
    !isSubmitting;

  const value = {
    customers,
    questions,
    isLoading,
    error,
    selectedCustomerId,
    answers,
    result,
    isSubmitting,
    canSubmit,
    selectCustomer,
    answerQuestion,
    submit,
    reset,
  };

  return <AssessmentContext.Provider value={value}>{children}</AssessmentContext.Provider>;
}

// eslint-disable-next-line react-refresh/only-export-components
export function useAssessment() {
  const context = useContext(AssessmentContext);
  if (context === null) {
    throw new Error('useAssessment must be used inside AssessmentProvider');
  }
  return context;
}
