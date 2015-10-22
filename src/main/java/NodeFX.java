/**
 * Created by bruce on 22/10/15.
 */

import javafx.scene.paint.Color;
import java.util.Random;

public class NodeFX {
    public int px, py;
    public int dim_rag, area_non_amm, dist_nodi;
    public int max_nodi;
    public Color colore;

    public NodeFX(int px, int py) {
        this.px = px;
        this.py = py;
        dim_rag = 5;
        area_non_amm = 8;
        dist_nodi = 16;
        Random rand = new Random();
        int r = rand.nextInt(240);
        int g = rand.nextInt(240);
        int b = rand.nextInt(240);
        colore = Color.rgb(r,g,b);
        max_nodi = 10;
    }

    public NodeFX(int px, int py, Color colore) {
        this.px = px;
        this.py = py;
        this.colore = colore;
        dim_rag = 2;
        area_non_amm = 5;
        dist_nodi = 10;
        max_nodi = 10;
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

    public boolean setArea_non_amm(int area_non_amm) {
        if(area_non_amm > dim_rag)
            this.area_non_amm = area_non_amm;
        else
            return false;
        return true;
    }

    public int getDist_nodi() {
        return dist_nodi;
    }

    public boolean setDist_nodi(int dist_nodi) {
        if(dist_nodi > 2*area_non_amm)
            this.dist_nodi = dist_nodi;
        else
            return false;
        return true;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeFX)) return false;

        NodeFX nodeFX = (NodeFX) o;

        if (px != nodeFX.px) return false;
        return py == nodeFX.py;

    }

    @Override
    public int hashCode() {
        int result = px;
        result = 31 * result + py;
        return result;
    }
}
