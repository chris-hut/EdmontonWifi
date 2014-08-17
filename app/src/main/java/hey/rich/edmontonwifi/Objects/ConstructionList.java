package hey.rich.edmontonwifi.Objects;

import java.util.List;

/**
 * Created by chris on 17/08/14.
 */
public class ConstructionList {

    private List<Construction> constructions;

    public void setAllConstructions(List<Construction> constructions) {
        this.constructions = constructions;
        //notifyViews();
    }

    public Construction getConstructionAtPos(int position){
        return constructions.get(position);
    }

    public List<Construction> getAllConstructions(){
        return this.constructions;
    }

}
