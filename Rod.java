enum Rod
{
  A, B, C;

  static Rod third (Rod one, Rod another)
  {
    if (one != Rod.A && another != Rod.A)
      return Rod.A;
    else if (one != Rod.B && another != Rod.B)
      return Rod.B;
    else
       return Rod.C;
  }
}
