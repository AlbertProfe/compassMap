import axios from 'axios';
import { useEffect, useState } from 'react';

export default function CompassMap() {
  const [assessments, setAssessments] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    axios
    .get('/api/v1/assessments/all')
    .then(({ data }) => {
      setAssessments(data);
      setIsLoading(false);
    });
  }, []);

  return (
    <section>
      <h2>Assessments</h2>
      {isLoading ? (
        <p>Loading assessments...</p>
      ) : assessments.length === 0 ? (
        <p>No assessments found.</p>
      ) : (
        <ul className="assessment-list">
          {assessments.map((assessment) => (
            <li className="assessment-card" key={assessment.id}>
              <h3>
                {assessment.customer
                  ? `${assessment.customer.firstName} ${assessment.customer.lastName}`
                  : 'No customer'}
              </h3>
              <p>Profile: {assessment.profile?.name || 'No profile'}</p>
              <p>Questions: {assessment.qtyQuestions}</p>
              <p>
                Created:{' '}
                {assessment.timestamp
                  ? new Date(assessment.timestamp).toLocaleString()
                  : 'Unknown'}
              </p>
            </li>
          ))}
        </ul>
      )}
    </section>
  );
}
