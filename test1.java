abstract class a{
abstract public void shape();


}
class rectangle extends a{
  int dim1;
  int dim2;
  rectangle(int t,int p){
    dim1=t;
    dim2=p;
  }
  public void shape(){
    int area=dim1*dim2;
    System.out.println("rectangle area is"+area);
  }
}
class circle extends a{
  int dim1;
  circle(int r){
    dim1=r;
  }
  public void shape(){
    double f=3.14;
    double area=f*dim1*dim1;
    System.out.println("circle area is"+area);
  }
}
class triangle extends a{
  int dim1;
  int dim2;
  triangle(int b,int h ){
    dim1=b;
    dim2=h;

  }
  public void shape(){
    int area=dim1*dim2;
    System.out.println("triangle area is"+area);
  }
}
class test1{
  a fig=new fig
  a ref;
  rectangle r=new rectangle(20,40);
  circle c=new circle(20);
  triangle t=new triangle(20,80);
  public static void main(String args[]){
    ref=r;
    ref.shape();
    ref=c;
    ref.shape();
    ref=t;
    t.shape();
  }
}
