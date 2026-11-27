import { useAssessment } from '../context/AssessmentContext.jsx';

export default function CustomerSelect() {
  const { customers, selectedCustomerId, selectCustomer, result } = useAssessment();

  if (customers.length === 0) {
    return <p>No customers in the database.</p>;
  }

  return (
    <div className="customer-select">
      <label htmlFor="customer">Customer</label>
      <select
        id="customer"
        value={selectedCustomerId}
        onChange={(e) => selectCustomer(e.target.value)}
        disabled={result !== null}
      >
        <option value="">-- choose a customer --</option>
        {customers.map((customer) => (
          <option key={customer.id} value={customer.id}>
            {customer.firstName} {customer.lastName}
          </option>
        ))}
      </select>
    </div>
  );
}
