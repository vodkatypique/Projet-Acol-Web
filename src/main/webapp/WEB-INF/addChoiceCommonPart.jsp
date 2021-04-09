<table>
    <tr>
        <th>Faut-il être passé par un paragraphe précédemment ?</th>
        <td><input type="checkbox" onclick="ableDisable()" name="isConditional" value="yesss" ></td>
        <td>=> lequel ?</td>
        <td> <select name="conditionalToWhich" disabled="true" id="element"> 
                <c:forEach items="${listPara}" var="para">
                    <c:if test="${para.id != numParagraph}"> <!-- pas conditionnel à lui-même, aucun sens -->
                        <option value="${para.id}">${para.title}</option>
                    </c:if>
                </c:forEach>
        </select></td>
    </tr>
</table>
