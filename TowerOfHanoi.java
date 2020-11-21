// Copyright (C) 2020 Jarmo Hurri

// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.

import java.util.Stack;
import java.util.EnumMap;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;

public class TowerOfHanoi
{
  public TowerOfHanoi (int numDiscs)
  {
    towers = new EnumMap<> (Rod.class);
    for (Rod rod : Rod.values ())
      towers.put (rod, new Stack<> ());
    
    for (int i = numDiscs; i >= 1; i--)
      towers.get (Rod.A).push (i);
    
    this.numDiscs = numDiscs;
    moves = new LinkedList <> ();
  }

  public void move (Rod from, Rod to) throws IllegalTowerOfHanoiMoveException
  {
    if (isEmpty (from) || !isEmpty (to) && peek (from) > peek (to))
      throw new IllegalTowerOfHanoiMoveException ("illegal move: " + from + " -> " + to);
    
    towers.get (to).push (towers.get (from).pop ());
    moves.add (new Move (from, to));
  }
    
  public int getNumDiscs ()
  {
    return numDiscs;
  }
  
  public Integer[] getDiscs (Rod rod)
  {
    return towers.get (rod).toArray (new Integer [0]);
  }
  
  public boolean isEmpty (Rod rod)
  {
    return towers.get (rod).empty ();
  }
  
  public int peek (Rod rod)
  {
    if (isEmpty (rod))
      return 0;
    else
      return (towers.get (rod).peek ());
  }
  
  public Queue<Move> getMoves ()
  {
    return moves;
  }
  
  public String toString ()
  {
    StringBuffer strBuf = new StringBuffer ();
    for (Rod rod : Rod.values ())
      strBuf.append (rod + ": " + towers.get (rod) + " \n");

    return strBuf.toString ();
      
  }
  
  private Map<Rod,Stack<Integer>> towers;
  private Queue<Move> moves;
  private int numDiscs;
}
