<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="pagedData" required="true" type="br.com.jardelnovaes.taxbr.persitence.PagedData"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

FIRST: ${pagedData.fistDisplayablePage} <br/>
LAST: ${pagedData.lastDisplayablePage} <br/>
	
<c:if test="${pagedData.pageCount > 1}">
	<nav>
	  <ul class="pagination">
		<c:if test="${pagedData.pageNumber != 0}">
			<li class="Previous">
			  <a href="#getPage-1" aria-label="Previous">
				<span aria-hidden="true">&laquo;</span>
			  </a>
			</li>
		</c:if>
		<!-- 
		<c:if test="${pagedData.firstLinkedPage > 0}">
			<li>
			  <a href="#0"><span aria-hidden="true">1</span></a>
			</li>
		</c:if>
		<c:if test="${pagedData.firstLinkedPage > 1}">
			<li>
			  <a href="#0"><span class="pagingDots">...</span></a>
			</li>
		</c:if>
		 -->
		<c:forEach begin="0" end="${pagedData.pageCount}" var="i">
			<c:choose>
				<c:when test="${pagedData.pageNumber == i}">
					<li><a class="active" href="?pgNum=${i}">${i+1}</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="?pgNum=${i}">${i+1}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<!-- 
		<c:if test="${pagedData.lastLinkedPage < pagedListHolder.pageCount - 2}">
		<li>
		  <a href="#0"><span class="pagingDots">...</span></a>
		</li>
		</c:if>
		<c:if test="${pagedData.lastLinkedPage < pagedListHolder.pageCount - 1}">
		<li>
		  <a href="#pageCount-1" aria-label="Next">
			<span aria-hidden="true">pc-1</span>
		  </a>
		</li>
		</c:if>
		 -->
		<c:if test="${pagedData.lastPage}">
		<li class="next">
		  <a href="#nextpage+1" aria-label="Next">
			<span aria-hidden="true">next</span>
		  </a>
		</li>
		</c:if>
	  </ul>
	</nav>
	<jsp:doBody/>
</c:if>

