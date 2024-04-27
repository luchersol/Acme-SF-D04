<%--
- form.jsp
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

<h2>
	<acme:message code="sponsor.sponsor-form.form.title.general-indicators"/>
</h2>

<table class="table table-sm">

	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-form.form.label.total-invoices-with-tax-less-than-or-equal-to-21"/>
		</th>
		<td>
			<acme:print value="${totalInvoicesWithTaxLessThanOrEqualTo21}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-form.form.label.total-sponsorship-with-link"/>
		</th>
		<td>
			<acme:print value="${totalSponsorshipWithLink}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-form.form.label.average-amount-of-the-sponsorships"/>
		</th>
		<td>
			<acme:print value="${averageAmountOfTheSponsorships}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-form.form.label.deviation-amount-of-the-sponsorships"/>
		</th>
		<td>
			<acme:print value="${deviationAmountOfTheSponsorships}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-form.form.label.minimum-amount-of-the-sponsorships"/>
		</th>
		<td>
			<acme:print value="${minimumAmountOfTheSponsorships}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-form.form.label.maximum-amount-of-the-sponsorships"/>
		</th>
		<td>
			<acme:print value="${maximumAmountOfTheSponsorships}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-form.form.label.average-quantity-of-the-invoices"/>
		</th>
		<td>
			<acme:print value="${averageQuantityOfTheInvoices}"/>
		</td>
	</tr>

	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-form.form.label.deviation-quantity-of-the-invoices"/>
		</th>
		<td>
			<acme:print value="${deviationQuantityOfTheInvoices}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-form.form.label.minimum-quantity-of-the-invoices"/>
		</th>
		<td>
			<acme:print value="${minimumQuantityOfTheInvoices}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-form.form.label.maximum-quantity-of-the-invoices"/>
		</th>
		<td>
			<acme:print value="${maximumQuantityOfTheInvoices}"/>
		</td>
	</tr>	
	
</table>