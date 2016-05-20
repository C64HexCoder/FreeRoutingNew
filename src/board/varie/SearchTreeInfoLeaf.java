package board.varie;

import board.shape.ShapeSearchTree;
import board.shape.ShapeTreeLeaf;
import freert.planar.ShapeTile;

public final class SearchTreeInfoLeaf
   {
   public final ShapeSearchTree tree;
   public ShapeTreeLeaf[] entry_arr;
   public ShapeTile[] precalculated_tree_shapes;

   public SearchTreeInfoLeaf(ShapeSearchTree p_tree)
      {
      tree = p_tree;
      entry_arr = null;
      precalculated_tree_shapes = null;
      }
   }
