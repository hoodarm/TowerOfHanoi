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

import javax.swing.SwingUtilities;

public class TOHApp
{
  public static void main (String[] args)
  {
    SwingUtilities.invokeLater (() -> new TOHApp ());
  }
  
  public static void move (TowerOfHanoi toh, int numDiscs, Peg from, Peg to, Peg intermediate)
    throws IllegalTowerOfHanoiMoveException
  {
    if (numDiscs==1)
    {
      toh.move(from,to);
      return;
    }
    move (toh,numDiscs-1,from,intermediate, to); // move top disc to intermediate
    toh.move (from, to); // move next disc to target
    move (toh,numDiscs-1,intermediate,to, from); // move top disc to intermediate
  }
  
  public TOHApp ()
  {
    // construct a new tower of Hanoi with 5 discs
    TowerOfHanoi toh = new TowerOfHanoi (8);
    
    try
    {
      // task is to move all discs from peg A to peg C
      move (toh, toh.getNumDiscs (), Peg.A, Peg.C, Peg.B);
    }
    catch (IllegalTowerOfHanoiMoveException e)
    {
      System.out.println ("illegal move: " + e);
    }
    
    new TOHUserInterface (toh);
  }
}
