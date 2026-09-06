function ApplicationCard({ application }) {
  return (
    <div>
      <h2>{application.internshipTitle}</h2>

      <p>
        <strong>Company:</strong> {application.companyName}
      </p>

      <p>
        <strong>Application date:</strong> {application.applicationDate}
      </p>

      <p>
        <strong>Status:</strong> {application.status}
      </p>

      {application.comment && (
        <p>
          <strong>Comment:</strong> {application.comment}
        </p>
        //if the comment is empty, it will not be displayed
      )}
    </div>
  );
}

export default ApplicationCard;