/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.tug.oad.travelsales.controller;

import at.tug.oad.travelsales.model.IBaseGame;
import at.tug.oad.travelsales.model.ILine;
import at.tug.oad.travelsales.model.IPoint;
import at.tug.oad.travelsales.model.IUser;
import at.tug.oad.travelsales.model.impl.Line;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Martin
 */
public class PointSelection {

    private IPoint selected = null;
    private IPoint lastSelected = null;
    
    private Collection<ILine> lines;
    
    public PointSelection() {
        lines = new ArrayList<>();
    }
    
    public void selectPoint (IPoint select)
    {
        lastSelected = selected;
        selected=select;
        
        if(lastSelected!=null && !selected.equals(lastSelected))
        {
            IUser user = TravelSalesViewController.getInstance().getActiveUser();
            IBaseGame basegame = TravelSalesViewController.getInstance().getSelectedBaseGame();
            Line line = new Line(user, basegame, lastSelected, select);
            lines.add(line);
            lastSelected=null;
            selected=null;
        }
    }

    public Collection<ILine> getLines() {
        return lines;
    }

    public void clearLines() {
        this.lines.clear();
    }

    public IPoint getSelectedPoint() {
        return selected;
    }
}
