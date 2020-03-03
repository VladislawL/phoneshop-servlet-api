<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<div class="col xl4 l4 m4 filter">
    <div class="container">
        <h3>Filters</h3>
        <form>
            <div class="row">
                <div class="input-field col s6">
                    <input placeholder="0" id="min-price" type="text" class="validate">
                    <label for="min-price">Min price</label>
                </div>
                <div class="input-field col s6">
                    <input placeholder="1000"  id="max-price" type="text" class="validate">
                    <label for="max-price">Max price</label>
                </div>
            </div>
        </form>
    </div>
</div>