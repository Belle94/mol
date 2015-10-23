import javafx.scene.paint.Color;

/**
 * Implement the Graphical Edge Class
 */
public class EdgeFX {
    NodeFX startNode, finalNode;
    Color color;
    Boolean oriented;
    Double weight;

    /**
     * Edge Constructor. An edge link two Node, from start to final.
     * If the edge is an oriented edge, it can be traveled from
     * startNode to finalNode and not from final to start.
     * If the edge isn't oriented (false) can be traveled in any way.
     * @param startNode node where start the edge.
     * @param finalNode node were ends the edge.
     * @param color the color of the node.
     * @param oriented boolean value: true if the edge is oriented.
     * @param weight the weight of the node.
     */
    public EdgeFX(NodeFX startNode, NodeFX finalNode, Color color, Boolean oriented, Double weight) {
        this.startNode = startNode;
        this.finalNode = finalNode;
        this.color = color;
        this.oriented = oriented;
        this.weight = weight;
    }

    public EdgeFX(NodeFX startNode, NodeFX finalNode, Boolean oriented, Double weight) {
        this(startNode, finalNode, Color.CORNFLOWERBLUE, oriented, weight);
    }

    public NodeFX getStartNode() {
        return startNode;
    }

    public void setStartNode(NodeFX startNode) {
        this.startNode = startNode;
    }

    public NodeFX getFinalNode() {
        return finalNode;
    }

    public void setFinalNode(NodeFX finalNode) {
        this.finalNode = finalNode;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Boolean getOriented() {
        return oriented;
    }

    public void setOriented(Boolean oriented) {
        this.oriented = oriented;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EdgeFX edgeFX = (EdgeFX) o;

        if (startNode != null ? !startNode.equals(edgeFX.startNode) : edgeFX.startNode != null) return false;
        if (finalNode != null ? !finalNode.equals(edgeFX.finalNode) : edgeFX.finalNode != null) return false;
        return !(oriented != null ? !oriented.equals(edgeFX.oriented) : edgeFX.oriented != null);

    }

    @Override
    public int hashCode() {
        int result = startNode != null ? startNode.hashCode() : 0;
        result = 31 * result + (finalNode != null ? finalNode.hashCode() : 0);
        result = 31 * result + (oriented != null ? oriented.hashCode() : 0);
        return result;
    }
}
