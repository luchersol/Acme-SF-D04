<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.auditRecord.list.label.code" path="code" width="20%"/>
	<acme:list-column code="auditor.auditRecord.list.label.draftMode" path="draftMode" width="20%"/>	
	<acme:list-column code="auditor.auditRecord.list.label.startDate" path="startDate" width="20%"/>
	<acme:list-column code="auditor.auditRecord.list.label.endDate" path="endDate" width="20%"/>
	<acme:list-column code="auditor.auditRecord.list.label.mark" path="mark" width="20%"/>
</acme:list>

	<jstl:if test="${ showCreate == true}">
		<acme:button test="${showCreate}" code="auditor.auditRecord.list.button.create" action="/auditor/audit-record/create?masterId=${masterId}"/>
	</jstl:if>

