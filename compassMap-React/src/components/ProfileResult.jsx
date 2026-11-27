import { useAssessment } from '../context/AssessmentContext.jsx';

export default function ProfileResult() {
  const { result, reset } = useAssessment();

  if (!result) return null;

  const { customer, profile } = result;
  const roadmaps = profile?.recommendedRoadmaps || [];

  return (
    <section className="profile-result">
      <div className="assessment-card">
        <h3>
          {customer ? `${customer.firstName} ${customer.lastName}` : 'Customer'}: {profile?.name}
        </h3>
        <dl>
          <div>
            <dt>Code profile</dt>
            <dd>{profile?.codeProfile}</dd>
          </div>
          <div>
            <dt>Description</dt>
            <dd>{profile?.description}</dd>
          </div>
          <div>
            <dt>Goal</dt>
            <dd>{profile?.goal}</dd>
          </div>
        </dl>
      </div>

      <h3>Recommended roadmaps</h3>
      {roadmaps.length === 0 ? (
        <p>No roadmaps linked to this profile.</p>
      ) : (
        <ul className="assessment-list">
          {roadmaps.map((roadmap) => (
            <li className="assessment-card" key={roadmap.id}>
              <h3>{roadmap.name}</h3>
              <p>{roadmap.description}</p>
              <p>{roadmap.steps} steps</p>
            </li>
          ))}
        </ul>
      )}

      <button type="button" className="view-button" onClick={reset}>
        Start again
      </button>
    </section>
  );
}
