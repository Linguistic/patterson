package com.linguistic.patterson.data.references

import com.linguistic.patterson.models.local.Reference

class AllSetChinese
    extends Reference(id = "asl",
                      name = "AllSet Chinese Grammar Wiki",
                      url = "https://resources.allsetlearning.com/chinese/grammar/ASG7UE4H") {}

object AllSetChinese {
  def apply() = new AllSetChinese()
}
