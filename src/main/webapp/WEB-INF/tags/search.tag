<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<div class="row">
    <div class="col s8">
        <form method="get">
            <div class="row">
                <div class="input-field col s9">
                    <input id="icon_prefix" type="text" class="validate" value="<c:out value="${param.query}"/>" name="query">
                    <label for="icon_prefix">Search</label>
                </div>
                <button class="btn waves-effect waves-light col s3" type="submit">
                    Search<i class="material-icons right">search</i>
                </button>
            </div>
        </form>
    </div>
    <div class="col s4">
        <div class="right">
            <a class='dropdown-trigger btn' data-target='dropdown1'>Sort</a>
            <!-- Dropdown Structure -->
            <ul id='dropdown1' class='dropdown-content'>
                <li><tags:sortLink field="price" order="asc">Price</tags:sortLink></li>
                <li><tags:sortLink field="price" order="desc">Price</tags:sortLink></li>
                <li><tags:sortLink field="description" order="asc">Description</tags:sortLink></li>
                <li><tags:sortLink field="description" order="desc">Description</tags:sortLink></li>
            </ul>
        </div>
    </div>
</div>