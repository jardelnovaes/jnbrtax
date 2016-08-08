<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="pagedData" required="true" type="br.com.jardelnovaes.taxbr.persitence.PagedData"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${pagedData.pageCount > 1}">
	<c:set var="orderUrl" value="" />
	<c:if test="${not empty pagedData.orderPropertyName}">
		<c:set var="orderUrl" value="&asc=${pagedData.orderPropertyName}" />
		<c:if test="${pagedData.descending}">
			<c:set var="orderUrl" value="&desc=${pagedData.orderPropertyName}" />
		</c:if>	
	</c:if>
	
	<nav>
	  <ul class="pagination">
		<li class="Previous">
		  <a href="?pgNum=${pagedData.previousPage}${orderUrl}" aria-label="Previous">
			<span aria-hidden="true">&laquo;</span>
		  </a>
		</li>				
		<c:forEach begin="${pagedData.firstDisplayablePage}" end="${pagedData.lastDisplayablePage}" var="i">
		<c:choose>
			<c:when test="${pagedData.pageNumber == i}">
				<li class="active"><a href="?pgNum=${i}${orderUrl}">${i+1}</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="?pgNum=${i}${orderUrl}">${i+1}</a></li>
			</c:otherwise>
		</c:choose>
		</c:forEach>		
		<li class="next">
		  <a href="?pgNum=${pagedData.nextPage}${orderUrl}"aria-label="Next">
			<span aria-hidden="true">&raquo;</span>
		  </a>
		</li>		
	  </ul>
	</nav>	
</c:if>


