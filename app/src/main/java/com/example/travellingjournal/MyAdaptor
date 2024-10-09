

class MyAdapter(
     private val itemList:MutableList<UserData>,
     private val onItemDelete:(Pair<UserData,Int>)->Unit,
     private val showDetails:(UserData)->Unit
     ):RecyclerView.Adapter<RecyclerView.ViewHolder>() {


     /*fun setData(list:MutableList<UserData>){
         val myDiffUtil:MyDiffUtil= MyDiffUtil(userList,list)
         val diffResult= DiffUtil.calculateDiff(myDiffUtil)
         userList=list
         diffResult.dispatchUpdatesTo(this)
     }*/

     companion object{
         const val TYPE_ONE=1
         const val TYPE_TWO=2
     }



     override fun getItemViewType(position: Int): Int {
         return when(itemList[position].type){
             1-> TYPE_ONE
             else-> TYPE_TWO
         }
     }

    inner class MyViewHolderOne(val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindData(itemData: UserData) {
            binding.idList.text=itemData.id.toString()
            binding.userNameList.text=itemData.name
            binding.emailList.text=itemData.mail
            binding.phoneList.text=itemData.phone
            binding.delate.setOnClickListener{
                onItemDelete(Pair(itemData,adapterPosition))
            }
            binding.detailsButton.setOnClickListener{

                showDetails(itemData)
            }

        }}

     inner class MyViewHolderTwo(val binding:ItemLaoutTwoBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindData(itemData: UserData) {
            binding.id.text=itemData.id.toString()
            binding.name.text=itemData.name
            binding.mail.text=itemData.mail
            binding.phone.text=itemData.phone
            binding.delate.setOnClickListener{
                onItemDelete(Pair(itemData,adapterPosition))
            }
        }}


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
           when(viewType){
               TYPE_ONE->{
                   val binding =
                       ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                   return MyViewHolderOne(binding)
               }
               else->{
                   val binding =
                       ItemLaoutTwoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                   return MyViewHolderTwo(binding)
               }
           }
        }

        override fun getItemCount() = itemList.size
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when(holder.itemViewType){
                TYPE_ONE->(holder as MyViewHolderOne).bindData(itemList[position])
                else->(holder as MyViewHolderTwo).bindData(itemList[position])
            }

        }


    }
