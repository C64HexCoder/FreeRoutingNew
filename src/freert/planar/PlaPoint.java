/*
 *  Copyright (C) 2014  Alfons Wirtz  
 *   website www.freerouting.net
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License at <http://www.gnu.org/licenses/> 
 *   for more details.
 *
 * Point.java
 *
 * Created on 1. Februar 2003, 11:38
 */

package freert.planar;

import java.io.Serializable;


/**
 * Abstract class describing functionality for Points in the plane.
 *
 * @author Alfons Wirtz
 */

public abstract class PlaPoint implements PlaObject, Serializable
   {
   private static final long serialVersionUID = 1L;

   // Standard implementation of the zero point .
   public static final PlaPointInt ZERO = new PlaPointInt(0, 0);
   
   /**
    * returns the translation of this point by p_vector
    */
   public abstract PlaPoint translate_by(PlaVector p_vector);

   /**
    * returns the difference vector of this point and p_other
    */
   public final PlaVector difference_by(PlaPoint p_other)
      {
      if ( p_other == null ) return null;
      
      if ( p_other instanceof PlaPointInt )
         return difference_by((PlaPointInt)p_other);
      else if ( p_other instanceof PlaPointRational )
         return difference_by((PlaPointRational)p_other);
      else 
         return null;
      }

   
   protected abstract PlaVector difference_by(PlaPointInt p_other);
   protected abstract PlaVector difference_by(PlaPointRational p_other);
   
   /**
    * approximates the coordinates of this point by float coordinates
    */
   public abstract PlaPointFloat to_float();

   /**
    * creates the smallest Box with integer coordinates containing this point.
    */
   public abstract ShapeTileBox surrounding_box();

   /**
    * creates the smallest Octagon with integer coordinates containing this point.
    */
   public abstract ShapeTileOctagon surrounding_octagon();

   /**
    * Returns true, if this point lies in the interiour or on the border of p_box.
    */
   public abstract boolean is_contained_in(ShapeTileBox p_box);

   public abstract PlaSide side_of(PlaLineInt p_line);

   /**
    * returns the nearest point to this point on p_line
    */
   public abstract PlaPoint perpendicular_projection(PlaLineInt p_line);



   /**
    * The function returns Side.ON_THE_LEFT, if this Point is on the left of the line from p_1 to p_2; 
    * Side.ON_THE_RIGHT, if this Point is on the right of the line from p_1 to p_2; 
    * Side.COLLINEAR, if this Point is collinear with p_1 and p_2.
    */
   public PlaSide side_of(PlaPoint p_1, PlaPoint p_2)
      {
      PlaVector v1 = difference_by(p_1);
      PlaVector v2 = p_2.difference_by(p_1);
      return v1.side_of(v2);
      }

   /**
    * Calculates the perpendicular direction froma this point to p_line. Returns Direction.NULL, if this point lies on p_line.
    */
   public PlaDirection perpendicular_direction(PlaLineInt p_line)
      {
      PlaSide side = side_of(p_line);
      
      if (side == PlaSide.COLLINEAR) return PlaDirection.NULL;
      
      if (side == PlaSide.ON_THE_RIGHT)
         return p_line.direction().turn_45_degree(2);
      else
         return p_line.direction().turn_45_degree(6);
      }

   /**
    * Returns 1, if this Point has a strict bigger x coordinate than p_other, 0, if the x cooordinates are equal, and -1 otherwise.
    */
   public final int compare_x(PlaPoint p_other)
      {
      if ( this instanceof PlaPointInt )
         return -p_other.compare_x((PlaPointInt)this);
      else
         return -p_other.compare_x((PlaPointRational)this);
      }
   

   protected abstract int compare_x(PlaPointInt p_other);
   abstract int compare_x(PlaPointRational p_other);
   
   
   /**
    * Returns 1, if this Point has a strict bigger y coordinate than p_other, 0, if the y cooordinates are equal, and -1 otherwise.
    */
   public abstract int compare_y(PlaPoint p_other);

   /**
    * The function returns compare_x (p_other), if the result is not 0. Otherwise it returns compare_y (p_other).
    */
   public int compare_x_y(PlaPoint p_other)
      {
      int result = compare_x(p_other);
      
      if (result == 0) return compare_y(p_other);

      return result;
      }

   /**
    * Turns this point by p_factor times 90 degree around p_pole.
    */
   public PlaPoint turn_90_degree(int p_factor, PlaPoint p_pole)
      {
      PlaVector v = this.difference_by(p_pole);
      v = v.turn_90_degree(p_factor);
      return p_pole.translate_by(v);
      }

   /**
    * Mirrors this point at the vertical line through p_pole.
    */
   public PlaPoint mirror_vertical(PlaPoint p_pole)
      {
      PlaVector v = difference_by(p_pole);
      v = v.mirror_at_y_axis();
      return p_pole.translate_by(v);
      }

   /**
    * Mirrors this point at the horizontal line through p_pole.
    */
   public PlaPoint mirror_horizontal(PlaPoint p_pole)
      {
      PlaVector v = difference_by(p_pole);
      v = v.mirror_at_x_axis();
      return p_pole.translate_by(v);
      }

   // auxiliary functions needed because the virtual function mechanism
   // does not work in parameter position

   abstract PlaPoint translate_by(PlaVectorInt p_vector);

   abstract PlaPoint translate_by(PlaVectorRational p_vector);



   abstract int compare_y(PlaPointInt p_other);

   abstract int compare_y(PlaPointRational p_other);
   }