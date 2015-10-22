/**
 * Created by bruce on 22/10/15.
 */

import javafx.scene.paint.Color;

public class NodeFX {
    public int px, py;
    public int dim_rag, area_non_amm, dist_nodi;
    public int max_nodi;
    public Color colore;

    public NodeFX(int px, int py) {
        this.px = px;
        this.py = py;
    }

    public NodeFX(int px, int py, Color colore) {
        this.px = px;
        this.py = py;
        this.colore = colore;
    }

    public int getPx() {
        return px;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public int getPy() {
        return py;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public int getDim_rag() {
        return dim_rag;
    }

    public void setDim_rag(int dim_rag) {
        this.dim_rag = dim_rag;
    }

    public int getArea_non_amm() {
        return area_non_amm;
    }

    public void setArea_non_amm(int area_non_amm) {
        this.area_non_amm = area_non_amm;
    }

    public int getDist_nodi() {
        return dist_nodi;
    }

    public void setDist_nodi(int dist_nodi) {
        this.dist_nodi = dist_nodi;
    }

    public int getMax_nodi() {
        return max_nodi;
    }

    public void setMax_nodi(int max_nodi) {
        this.max_nodi = max_nodi;
    }

    public Color getColore() {
        return colore;
    }

    public void setColore(Color colore) {
        this.colore = colore;
    }
}
