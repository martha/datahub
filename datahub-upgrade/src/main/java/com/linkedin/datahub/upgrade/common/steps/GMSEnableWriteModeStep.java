package com.linkedin.datahub.upgrade.common.steps;

import com.linkedin.datahub.upgrade.UpgradeContext;
import com.linkedin.datahub.upgrade.UpgradeStep;
import com.linkedin.datahub.upgrade.UpgradeStepResult;
import com.linkedin.datahub.upgrade.impl.DefaultUpgradeStepResult;
import com.linkedin.entity.client.EntityClient;
import com.linkedin.metadata.Constants;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class GMSEnableWriteModeStep implements UpgradeStep {

  private final EntityClient _entityClient;

  @Override
  public String id() {
    return "GMSEnableWriteModeStep";
  }

  @Override
  public int retryCount() {
    return 2;
  }

  @Override
  public Function<UpgradeContext, UpgradeStepResult> executable() {
    return (context) -> {
      try {
        _entityClient.setWritable(true, Constants.SYSTEM_ACTOR);
      } catch (Exception e) {
        e.printStackTrace();
        context.report().addLine("Failed to turn write mode back on in GMS");
        return new DefaultUpgradeStepResult(id(), UpgradeStepResult.Result.FAILED);
      }
      return new DefaultUpgradeStepResult(id(), UpgradeStepResult.Result.SUCCEEDED);
    };
  }
}
