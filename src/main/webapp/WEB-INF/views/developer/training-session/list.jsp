<%--
- list.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<acme:list>
	<acme:list-column code="developer.training-session.list.label.code" path="code" width="20%"/>	
	<acme:list-column code="developer.training-session.list.label.timeStart" path="timeStart" width="15%"/>
	<acme:list-column code="developer.training-session.list.label.timeEnd" path="timeEnd" width="15%"/>
	<acme:list-column code="developer.training-session.list.label.contactEmail" path="contactEmail" width="20%"/>
</acme:list>

<acme:button test="${showCreate}" code="developer.training-session.list.button.create" action="/developer/training-session/create?masterId=${masterId}"/>
