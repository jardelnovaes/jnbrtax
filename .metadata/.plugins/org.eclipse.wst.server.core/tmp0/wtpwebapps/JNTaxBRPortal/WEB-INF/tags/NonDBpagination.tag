<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="pagedListHolder" required="true" type="org.springframework.beans.support.PagedListHolder"%>
<%@ attribute name="pageLink" required="true" type="java.lang.String"%>
<%@ attribute name="activeAction" required="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${pagedListHolder.pageCount > 1}">
	<nav>
	  <ul class="pagination">
		<c:if test="${!pagedListHolder.firstPage}">
			<li class="Previous">
			  <a href="#getPage-1" aria-label="Previous">
				<span aria-hidden="true">&laquo;</span>
			  </a>
			</li>
		</c:if>
		<c:if test="${pagedListHolder.firstLinkedPage > 0}">
			<li>
			  <a href="#0"><span aria-hidden="true">1</span></a>
			</li>
		</c:if>
		<c:if test="${pagedListHolder.firstLinkedPage > 1}">
			<li>
			  <a href="#0"><span class="pagingDots">...</span></a>
			</li>
		</c:if>
		<c:forEach begin="${pagedListHolder.firstLinkedPage}" end="${pagedListHolder.lastLinkedPage}" var="i">
			<c:choose>
				<c:when test="${pagedListHolder.page == i}">
					<li><a class="active" href="?pgNum=${i}">${i+1}</a></li>
				</c:when>
				<c:otherwise>
					<li><a class="active" href="?pgNum=${i}">${i+1}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${pagedListHolder.lastLinkedPage < pagedListHolder.pageCount - 2}">
		<li>
		  <a href="#0"><span class="pagingDots">...</span></a>
		</li>
		</c:if>
		<c:if test="${pagedListHolder.lastLinkedPage < pagedListHolder.pageCount - 1}">
		<li>
		  <a href="#pageCount-1" aria-label="Next">
			<span aria-hidden="true">pc-1</span>
		  </a>
		</li>
		</c:if>
		<c:if test="${pagedListHolder.lastPage}">
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

