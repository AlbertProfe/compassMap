import axios from 'axios';

const API = '/api/v1';

export const getCustomers = () => axios.get(`${API}/customers/all`).then((r) => r.data);

export const getQuestions = () => axios.get(`${API}/questions/all`).then((r) => r.data);

export const submitAssessment = (customerId, answers) =>
  axios
    .post(`${API}/assessments/submit`, {
      customer: { id: customerId },
      questionsAndAnswers: Object.entries(answers).map(([questionId, value]) => ({
        question: { id: questionId },
        answer: { value },
      })),
    })
    .then((r) => r.data);
