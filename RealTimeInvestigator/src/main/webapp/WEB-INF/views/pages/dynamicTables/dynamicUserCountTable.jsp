<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<display:table name="userCountTable" cellspacing="0" requestURI="./getCurrentUserCount" id="userCountTable" sort="external" partialList="true" size="${size}" pagesize="${gridSize}" export="false">
	<display:column property="countId" title="User No"/>
	<display:column property="deviceType" title="Device"/>
    <display:column  sortable="false" headerClass="text-center sortable sorted order1" title="Action" media="html" class="action">
		<div class="text-left">
			<input type="hidden" id="${userCountTable.sid}" value="${userCountTable.deviceType}">
			<button onclick="getUserDetailsBySessionId(${userCountTable.sid}, ${userCountTable.countId})" class="edit-btn btn btn-primary"><span class="fa fa-edit"> </span>More</button>
			<c:if test="${userCountTable.cssStatus == '1'}">
				<button onclick="setCSSandJSStatus(${userCountTable.sid}, 'css')" id="${userCountTable.sid}" class="delete-btn btn btn-danger"><span class="fa fa-edit"> </span>Disable CSS</button>
			</c:if>
			<c:if test="${userCountTable.cssStatus == '0'}">
				<button onclick="setCSSandJSStatus(${userCountTable.sid}, 'css')" id="${userCountTable.sid}" class="delete-btn btn btn-danger"><span class="fa fa-edit"> </span>Enable CSS</button>
			</c:if>
			
		</div>
	</display:column>
	<display:setProperty name="basic.empty.showtable" value="true" />
	<display:setProperty name="basic.msg.empty_list" value="" />
</display:table>