#include<stdio.h>
#include<stdlib.h>
struct node
{
	int info;
	struct node *ptr;
}*top,*top1,*temp;

void push(int data);
void pop();
void display();
void create();
int count=0;
void main()
{
	int  no,ch,e;
	printf("\n 1-push");
	printf("\n 2-pop");
	printf("\n 3-exit");
	printf("\n 4-display");
	
	create();
	while(1)
	{
		printf("\n enter the choice");
		scanf("%d",&ch);
		switch(ch)
		{
			case 1:
				printf("enter the data");
				scanf("%d",&no);
				push(no);
				break;
			case 2:
				pop();
				break;
			case 3:
				exit(0);


			
				break;
			case 4:
				display();
				break;
			
		}
	}
}
	void create()
	{
		top=NULL;
	}

	void push(int data)
	{
		if(top==NULL)
		{
			top=(struct node *) malloc(1*sizeof(struct node));
			top ->ptr=NULL;
			top ->info=data;
		}
		else
		{
			temp=(struct node*) malloc(1*sizeof(struct node));
			temp->ptr=top;
			temp->info=data;
			top=temp;
		}
		count++;
	}
	

	void display()
	{
		top1=top;
		if(top1==NULL)
		{
			printf("stack is empty");
			return;
		}
		while(top1 !=NULL)
		{
			printf("%d",top1->info);
			top1=top1->ptr;
		}
	}
	void pop()
	{
		top1=top;
		if(top1==NULL)
		{
			printf("empty stack");
			return;
		}
		else
		{
			top1=top1->ptr;
			
			printf("popped element is%d",top->info);
			free(top);
			top=top1;
			count--;
		}
	}
	
